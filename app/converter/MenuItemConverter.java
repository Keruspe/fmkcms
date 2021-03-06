package converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.menu.MenuItem;

/**
 *
 * @author waxzce
 */
public class MenuItemConverter implements JsonDeserializer<MenuItem>, JsonSerializer<MenuItem> {

   public MenuItem deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
      try {
         Class c = Class.forName(je.getAsJsonObject().get("classname").getAsString());
         Object obj = c.getConstructor(new Class[]{}).newInstance();
         Iterator<Entry<String, JsonElement>> it = je.getAsJsonObject().entrySet().iterator();
         Gson gson = new GsonBuilder().registerTypeAdapter(MenuItem.class, new MenuItemConverter()).create();
         while (it.hasNext()) {
            Entry<String, JsonElement> entry = it.next();
            if (entry.getKey().equals("classname") || entry.getKey().equals("id")) {
               continue;
            }
            Field f = c.getField(entry.getKey());
            f.set(obj, gson.fromJson(entry.getValue(), f.getType()));
         }
         Method m = c.getMethod("save", new Class[]{});
         m.invoke(obj, new Object[]{});
         return (MenuItem) obj;
      } catch (Exception ex) {
         Logger.getLogger(MenuItemConverter.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }

   public JsonElement serialize(MenuItem t, Type type, JsonSerializationContext jsc) {
      try {
         Gson gson = new Gson();
         Class c = Class.forName(t.getClassname());
         return gson.toJsonTree(c.cast(t));
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(MenuItemConverter.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }
}
