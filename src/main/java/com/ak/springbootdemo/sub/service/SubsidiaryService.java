package com.ak.springbootdemo.sub.service;

import com.ak.springbootdemo.sub.data.Subsidiary;
import com.ak.springbootdemo.sub.data.SubsidiaryRepository;
import com.ak.springbootdemo.sub.exceptions.SubsidiaryControllerException;
import com.ak.springbootdemo.sub.exceptions.SubsidiaryServiceException;
import com.ak.springbootdemo.sub.util.JSONSubsidiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Subsidiary Service
 */
@Service
public class SubsidiaryService {
    private final SubsidiaryRepository subsidiaryRepository;

    @Autowired
    public SubsidiaryService(SubsidiaryRepository subsidiaryRepository) {
        this.subsidiaryRepository = subsidiaryRepository;
    }

    /**
     * Get all Subsidiaries from DB.
     *
     * @return List of Subsidiaries sorted by name -> innerCode
     */
    public List<Subsidiary> getSubsidiaries() {
        Iterable<Subsidiary> subsidiaries = this.subsidiaryRepository.findAll();
        List<Subsidiary> subsidiariesList = new ArrayList<>();
        subsidiaries.forEach(subsidiariesList::add);

        subsidiariesList.sort(
                (o1, o2) -> {
                    if (o1.getName().equals(o2.getName())) {
                        return o1.getInnerCode().compareTo(o2.getInnerCode());
                    }
                    return o1.getName().compareTo(o2.getName());
                });

        return subsidiariesList;
    }

    /**
     * Create or Update Subsidiary entity
     *
     * @param innerCode   unique inner code of the subsidiary
     * @param address     subsidiary address
     * @param name        subsidiary name
     * @param phoneNumber subsidiary phoneNumber
     * @return Created or Updated Subsidiary entity
     */
    protected Subsidiary saveSubsidiary(String innerCode, String address, String name, String phoneNumber) {
        Optional<Subsidiary> optionalSub = subsidiaryRepository.findByInnerCode(innerCode);
        optionalSub.ifPresent(sub -> sub.update(innerCode, address, name, phoneNumber));
        return optionalSub.orElse(addSubsidiary(new Subsidiary(innerCode, address, name, phoneNumber)));
    }

    /**
     * Find Subsidiary by inner code.
     *
     * @param subsidiary Subsidiary entity that have to be added into DB
     * @return Subsidiary entity that was added into DB
     */
    public Subsidiary addSubsidiary(Subsidiary subsidiary) {
        if (null == subsidiary) {
            throw new SubsidiaryServiceException("Subsidiary cannot be null.");
        }
        this.subsidiaryRepository.save(subsidiary);
        return subsidiary;
    }

    /**
     * Read and save Subsidiary entities from an external JSON file into DB
     *
     * @return subsidiaries list from json file
     */
    public List<JSONSubsidiary> saveSubsidiariesFromJSONFile() throws SubsidiaryControllerException {
        List<JSONSubsidiary> jsonSubsidiaryList;
        try {
            jsonSubsidiaryList = JSONSubsidiary.read();
            jsonSubsidiaryList.forEach(importedSubsidiary ->
                    saveSubsidiary(
                            importedSubsidiary.getInnerCode(),
                            importedSubsidiary.getAddress(),
                            importedSubsidiary.getName(),
                            importedSubsidiary.getPhoneNumber()));

        } catch (IOException e) {
            throw new SubsidiaryControllerException("Unable get subsidiaries list from json file.");
        }
        return jsonSubsidiaryList;
    }
}