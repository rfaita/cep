package com.cep.test.repository;

import com.cep.server.model.Cep;
import com.cep.server.repository.CepRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.cep.test.helper.CepBuilder.createCompleteCep;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CepRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CepRepository cepRepository;

    private static Boolean init = false;

    @Before
    public void setUp() {
        if (!init) {
            mongoTemplate.save(createCompleteCep("12345678"));
            mongoTemplate.save(createCompleteCep("98765432"));
            mongoTemplate.save(createCompleteCep("98765431"));
            init = true;
        }
    }

    @Test
    public void find_by_cep_with_success() {

        Cep ret = cepRepository.findByCep("12345678");

        Assert.assertTrue(ret != null);
        Assert.assertEquals("bairro 12345678", ret.getBairro());
        Assert.assertEquals("logradouro 12345678", ret.getLogradouro());
        Assert.assertEquals("cidade 12345678", ret.getCidade());
        Assert.assertEquals("estado 12345678", ret.getEstado());
        Assert.assertEquals("uf 12345678", ret.getUf());
        Assert.assertEquals("123 12345678", ret.getId());
        Assert.assertEquals("12345678", ret.getCep());

    }

    @Test
    public void find_by_cep_not_found() {

        Cep ret = cepRepository.findByCep("1234567*");

        Assert.assertTrue(ret == null);

    }

    @Test
    public void find_by_index_with_success() {

        TextCriteria crit = TextCriteria.forLanguage("pt").caseSensitive(false).matchingAny("bairro");

        List<Cep> list = cepRepository.findAllByOrderByScoreDesc(crit, PageRequest.of(0, 20));

        Assert.assertEquals(3, list.size());

//        Cep ret = list.get(0);
//
//        Assert.assertTrue(ret != null);
//        Assert.assertEquals("bqwe", ret.getBairro());
//        Assert.assertEquals("lqwe", ret.getLogradouro());
//        Assert.assertEquals("cqwe", ret.getCidade());
//        Assert.assertEquals("eqwe", ret.getEstado());
//        Assert.assertEquals("uqwe", ret.getUf());
//        Assert.assertEquals("98765432", ret.getId());
//        Assert.assertEquals("98765432", ret.getCep());
//
//        ret = list.get(1);
//
//        Assert.assertTrue(ret != null);
//        Assert.assertEquals("bqwe", ret.getBairro());
//        Assert.assertEquals("lqwe", ret.getLogradouro());
//        Assert.assertEquals("cqwe", ret.getCidade());
//        Assert.assertEquals("eqwe", ret.getEstado());
//        Assert.assertEquals("uqwe", ret.getUf());
//        Assert.assertEquals("98765431", ret.getId());
//        Assert.assertEquals("98765431", ret.getCep());

    }


}
