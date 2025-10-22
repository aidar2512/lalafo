package kg.mega.lalafo.service.impl;

import kg.mega.lalafo.mapper.AdMapper;
import kg.mega.lalafo.model.Ad;
import kg.mega.lalafo.model.dto.AdApiDto;
import kg.mega.lalafo.repository.AdRepo;
import kg.mega.lalafo.service.LalafoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LalafoServiceImpl implements LalafoService {

    private final AdRepo productRepo;
    private final AdMapper productMapper;

    public LalafoServiceImpl(AdRepo productRepo) {
        this.productRepo = productRepo;
        this.productMapper = AdMapper.INSTANCE;
    }

    @Override
    public List<Ad> getAds() {
        List<AdApiDto> apiProducts = productRepo.fetchProducts();
        return productMapper.toProductList(apiProducts);
    }
}
