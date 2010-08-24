package crud;

import controllers.CRUDFieldProvider;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import models.Tag;

/**
 *
 * @author keruspe
 */
@CRUDFieldProvider(isMultiple = true, tagName = "crud.fmkcms.tagsfield")
public class TagsField {
    
    public static Set<Tag> process(Map<String, List<String>> values) {
        Set<Tag> returnTags = new TreeSet<Tag>();

        for (String tagsString : values.get("tags")) {
            for (String tag : Arrays.asList(tagsString.split(","))) {
                returnTags.add(Tag.findOrCreateByName(tag));
            }
        }

        return returnTags;
    }

    public static Set<String> getArgsList() {
        Set<String> s = new TreeSet<String>();
        s.add("tags");
        return s;
    }
    
}