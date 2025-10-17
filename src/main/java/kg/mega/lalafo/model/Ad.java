package kg.mega.lalafo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Ad {
    String title;
    String price;
    String city;
    String date;
    String imageUrl;

}
