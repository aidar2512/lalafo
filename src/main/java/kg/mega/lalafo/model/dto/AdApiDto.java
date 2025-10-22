package kg.mega.lalafo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdApiDto {
    String title;
    Long price;
    String city;
    @JsonProperty("images")
    private List<AdImageDto> images;
    boolean isNegotiable;

    @JsonProperty("created_time")
    Long createdTime;
    @JsonProperty("updated_time")
    Long updatedTime;

}
