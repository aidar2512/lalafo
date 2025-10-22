package kg.mega.lalafo.mapper;


import kg.mega.lalafo.model.Ad;
import kg.mega.lalafo.model.dto.AdApiDto;
import kg.mega.lalafo.model.dto.AdImageDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AdMapper {
    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    String PLACEHOLDER_IMAGE = "https://via.placeholder.com/220?text=Нет+фото";

    @Mapping(target = "price", source = ".", qualifiedByName = "formatPrice")
    @Mapping(target = "imageUrl", source = ".", qualifiedByName = "getImageUrl")
    @Mapping(target = "date", source = ".", qualifiedByName = "formatDate")
    Ad toProduct(AdApiDto item);

    List<Ad> toProductList(List<AdApiDto> items);

    @Named("formatPrice")
    default String formatPrice(AdApiDto item) {
        if (item.isNegotiable() || item.getPrice() == null || item.getPrice() <= 0) {
            return "Договорная";
        }
        return String.format("%,d KGS", item.getPrice());
    }

    @Named("getImageUrl")
    default String getImageUrl(AdApiDto item) {
        return Optional.ofNullable(item.getImages())
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(AdImageDto::getOriginalUrl)
                .orElse(PLACEHOLDER_IMAGE);
    }

    @Named("formatDate")
    default String formatDate(AdApiDto item) {
        Long timestamp = Optional.ofNullable(item.getUpdatedTime())
                .orElse(item.getCreatedTime());

        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("Asia/Bishkek"))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
