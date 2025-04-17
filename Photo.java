package directory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

// all of that methods not ready implemented
public class Photo {
    // all of that fields IDE propose to make final
    // cannot be reassigned //visible for other threads //better for javaCompile
    //title description and date I'm not going to change, so mb it will be better to have them final
    //tags and collection can be change though
    private String title;
    private final Set<String> tags; //all the tags unique and organized
    private final String description;
    private final Date date;
    private final String imagePath;
    private final Set<String> collections; // helps to organize and add photos
    //different collections(one or many) // collections 's' because have name conflict in 'add'

    private static final Pattern TITLE_PATTERN = Pattern.compile("^[A-Z][a-z]*(?: [a-z]+)*$");
    // First letter upper than allwed spaces and all other letters must start from small letter
    public Photo(/*String title,*/ Date date, String description, String imagePath){
//        this.title = title;
        this.date = date;
        this.imagePath = imagePath;
        this.description = description;
        this.tags = new HashSet<>();
        this.collections = new HashSet<>();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) throws IllegalAccessException {
        if(!TITLE_PATTERN.matcher(title).matches()){
            throw new IllegalAccessException("Invalid TITLE type!\nYou have to use\nLETTERS & no spaces");
        }
        this.title = title;
    }

    public Set<String> getTags() {
        return tags;
    }

    public Date getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
//    public Set<String> getCollections() {
//        return collections;
//    }
    public void addTag(String tag) {
        tags.add(tag);
    }
//    public void removeTag(String tag) { // I'm not sure that will be useful
//        tags.remove(tag);
//    }
    public String getImagePath() {
        return imagePath;
    }
    public void addCollection(String collection) {
        collections.add(collection);
    }
//    public void removeCollection(String collection) { // for that I need to have additional listener also don't sure it will be useful
//        collections.remove(collection);
//    }
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Title: " + title + ", \nDate: " + sdf.format(date) + ", \nTags: " + tags + ", \nDescription: " + description;
    }
}
