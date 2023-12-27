/**
 * A IMPLEMENTAR
 * 
 * Alguna manera de borrar los registros que no se puedan obtener descripcion ni titulo
 * 
 * Intentar page rank, si el link aparece en otra pagina sumarle un "punto", Ordenar los registros de ASC con esa columna de "puntuacion"
 * 
 */


package com.proyecto.proyectoBuscador.services;

import com.proyecto.proyectoBuscador.entities.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author chuky
 */
@Service
public class SpiderService {
    
    @Autowired
    SearchService searchService;
    
    /**
     * Obtiene links aun no indexados en la BD y los manda a indexar.
     */
    public void indexWebPages(){
        List<WebPage> linksToIndex = searchService.getLinksToIndex().toList();
        linksToIndex.stream().parallel().forEach(webPage -> {
            try {

                indexWebPage(webPage);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    
    /**
     * Procesa todo el contenido web para saber de que trata y guardarla en la base de datos.
     */
    private void indexWebPage(WebPage wp) throws Exception{
        String url = wp.getUrl();
        String content = getWebContent(url);
        if(content.isBlank()){ return; } //Si trae la pagina en blanco (vacia) devolver eso
        
        indexAndSave(content, wp);
        saveLinks(getDomain(url), content);
    }
    
    private String getWebContent(String link){
        try {
            //Seteo el link como URL
            URL url = new URL(link);
            //Creo la connection
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            //Tomo la cabecera de la web, el enconding
            String encoding = conn.getContentEncoding();
            
            //Obtengo la informacion dentro de la web
            InputStream input = conn.getInputStream();
            
            //Creo un Stream leyendo linea por linea la pagWeb y luego la colecto en un String
            Stream<String> lines = new BufferedReader(new InputStreamReader(input)).lines();
            
            return lines.collect(Collectors.joining()); // Devuelvo las lineas en un String
            
        }  catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return "";
    }
    
    private void indexAndSave(String content, WebPage wp){
        String title= getTitle(content);
        String description = getDescription(content);
        
        wp.setDescription(description);
        wp.setTitle(title);

        if(wp.getContadorRevision() == null) wp.setContadorRevision(0);
        wp.setContadorRevision(wp.getContadorRevision()+1);

        
        searchService.save(wp);
    }
    
    /**
    *  Guarda todos los links en la BD, seteandolos a un objeto de WebPage primero.  
    **/
    private void saveLinks(String domain, String content){
        List<String> links = getLinks(domain, content);
        
        links.stream().filter(link -> !searchService.exist(link))
                      .map(link -> new WebPage(link))
                      .forEach(webPage -> searchService.save(webPage));
        
    }
    
    private String getTitle(String content){
        String[] contenidoSplit = content.split("<title>");
        String aux= "";
        if(contenidoSplit.length > 1) {
            aux = Arrays.stream(contenidoSplit).toList().get(1);
            return aux.split("</title")[0];
        }
        return null;
    }
    
    private String getDescription(String content){
        String[] contenidoSplit = content.split("<meta name=\"description\" content=\"");
        String aux= "";
        if(contenidoSplit.length > 1) {
            aux = Arrays.stream(contenidoSplit).toList().get(1);
            return aux.split("\" />")[0].split("\"/>")[0].split("\">")[0];
        }
        return null;
    }
    
        
    private List<String> getLinks(String domain, String content){
        List<String> links = new ArrayList<>();
        
        String[] splitHref= content.split("href=\""); //Separo el contenido por los href
        List<String> listaHref = Arrays.asList(splitHref); //Comvierto el Arreglo en una Lista para poder manejarlo
        //listaHref.remove(0); //El primer elemento no contiene ningun link por lo que lo descarto - Se descarta ya que luego se filtra con el metodo cleanLinks.
        listaHref.forEach(str ->{
            links.add(str.split("\"")[0]); //Recorro todos los elementos y lo separo por ", por lo que el link siempre es el primer elemento y lo adhiero a la lista.
        });
        
        return cleanLinks(domain, links);
    }
    
    private String getDomain(String url) {
        String[] aux = url.split("/");
        return aux[0]+"//"+ aux[2];
    }
    
    /**
    *   Limpia los links que no hacen referencia a paginas en lo posible, para no llenar de basura la BD 
    **/
    private List<String> cleanLinks(String domain, List<String> links){
        
        String[] extensiones = new String[]{"css", "js", "jpg", "json", "png", "woff2"};
        
        
        
        List<String> resultExtensiones= links.stream().filter(link -> Arrays.stream(extensiones).noneMatch(extension -> link.endsWith(extension)))
                .filter(link -> (link.startsWith("https:") || link.startsWith("http:"))).toList();

        //List<String> resultTerminacion = resultExtensiones.stream().filter(link -> link.startsWith("https:")).collect(Collectors.toList());
        List<String> resultMap = resultExtensiones.stream().map(link -> link.startsWith("/") ? domain + link : link).toList();


        List<String> resultLinks = new ArrayList<>();
        resultLinks.addAll(resultMap);
        
        return resultLinks; 
        
    }
}
