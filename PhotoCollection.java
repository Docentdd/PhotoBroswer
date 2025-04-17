package directory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// that class is for manages of the collection in my application
// add remove methods to and from collection respectively
// get method will be useful for getting all the photos from the collection
//to do smth(search, display)
//search methods using streams for purpose (using 'or' and 'and')
public class PhotoCollection {
    private final List<Photo> photos;

    public PhotoCollection() { // constructor
        photos = new ArrayList<>();
    }

    public void addPhoto(Photo photo) {
        photos.add(photo); // uploading the photo using UI to the collection
    }

    public void removePhoto(Photo photo) { //remove that photo from the collection using UI
        photos.remove(photo);
    }

    //search will get true of false for searching all specified elem or any specified elem
    public List<Photo> searchByTags(Set<String> searchTags, boolean and) {
        if (and) { // .stream convert the 'photos' collection into a stream
            return photos.stream().filter(photo -> photo.getTags().containsAll(searchTags)).collect(Collectors.toList());
        } else {
            return photos.stream().filter(photo -> photo.getTags().stream().anyMatch(searchTags::contains)).collect(Collectors.toList());
        }
    }
    public List<Photo> searchByDescription(String keyword) { // to make it 'and' and 'or' search we can use Set<String>
        return photos.stream()
                .filter(photo -> photo.getDescription().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Photo> searchByDate(Date from, Date to) {
        return photos.stream()
                .filter(photo -> !photo.getDate().before(from) && !photo.getDate().after(to))
                .collect(Collectors.toList());
    }

    public List<Photo> getPhotos() {
        return photos;
    }

}
