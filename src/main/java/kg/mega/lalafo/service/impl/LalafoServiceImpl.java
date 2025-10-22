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

    private final AdRepo adRepo;
    private final AdMapper adMapper;

    public LalafoServiceImpl(AdRepo adRepo) {
        this.adRepo = adRepo;
        this.adMapper = AdMapper.INSTANCE;
    }

    @Override
    public List<Ad> getProducts() {
        List<AdApiDto> apiProducts = adRepo.fetchProducts();
        return adMapper.toProductList(apiProducts);
    }
}
