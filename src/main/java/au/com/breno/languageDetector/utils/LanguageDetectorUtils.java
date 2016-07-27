package au.com.breno.languageDetector.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author brenoreis
 *
 */
public class LanguageDetectorUtils {
	
	/**
     * Merges two hash maps
     * @param map1
     * @param map2
     * @return
     */
    public static Map<String, Integer> merge(Map<String, Integer> map1, Map<String, Integer> map2) {
        Map<String, Integer> map3 = new HashMap<String, Integer>();
        map3.putAll(map1);
        map3.putAll(map2);
        return map3;
    }
    
    /**
     * Sorts a map by value asc
     * @param unsortedMap
     * @return
     */
    public static Map<String, Integer> sortAsc(Map<String, Integer> unsortedMap) {

		// Convert Map to List
		List<Entry<String, Integer>> list =
			new LinkedList<Entry<String, Integer>>(unsortedMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1,
                                           Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}

    /**
     * Sorts a map by value desc
     * @param unsortedMap
     * @return
     */
    public static Map<String, Integer> sortDesc(Map<String, Integer> unsortedMap) {

        // Convert Map to List
        List<Entry<String, Integer>> list =
                new LinkedList<Entry<String, Integer>>(unsortedMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // Convert sorted map back to a Map
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Iterator<Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
            Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * Return top N entries from a map
     * @param map
     * @param topN
     * @return
     */
    public static Map<String, Integer> getTopValues(Map<String, Integer> map, int topN) {
    	
    	Map<String, Integer> topValuesMap = new HashMap<String, Integer>();
    	
    	int count = 0;
    	
    	for (Entry<String, Integer> entry : map.entrySet()) {
            count++;
            topValuesMap.put(entry.getKey(), count);
    		if (count >= topN) break;
    	}
    	
    	return topValuesMap;
    }

    /**
     * Returns the input text without digits and punctuation
     * @param text
     * @return
     */
    public static String prepareContent(String text) {
        return text
                //remove digits
                .replaceAll("\\d", "")
                //remove new line
                .replace("\n", "")
                .replace("\f", "")
                //remove punctuation
                .replaceAll("[\\Q,.;:?!\\E]", "")
                //remove duplicate spaces
                .replace("  ", " ")
                .toLowerCase();
    }

    /**
     * reads a file and returns a string
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * read files from path and consolidates them in a map
     * @param rootPath
     * @return
     * @throws IOException
     */
    public static Map<String, String> readFiles(String rootPath) throws IOException {
        Map<String, String> languageMap = new HashMap<String, String>();
        File folder = new File(rootPath);
        for (File file : folder.listFiles()) {
            String[] filename = file.getName().split("\\.", 2);
            String newContent = LanguageDetectorUtils.readFile(file.getPath(), Charset.defaultCharset());
            String content = languageMap.get(filename[0]);
            if (content != null) {
                content += " " + newContent;
            } else {
                content = newContent;
            }
            languageMap.put(filename[0], content);
        }
        return languageMap;
    }

}
