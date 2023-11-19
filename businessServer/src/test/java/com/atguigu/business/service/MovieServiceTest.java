package com.atguigu.business.service;

import co.elastic.clients.elasticsearch.core.GetRequest;
import com.atguigu.business.BaseTest;
import com.atguigu.business.model.domain.Insurance;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

public class MovieServiceTest extends BaseTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void test() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/users/login").param("username", "yanying").param("password","123"))
                .andExpect(MockMvcResultMatchers.view().name("itemEdit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(result.getModelAndView().getModel().get("item"));
    }

    @Test
    public void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/users/login").
                        param("username", "yanying").param("password","123")
                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("IPhone X"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Autowired
    private ElasticsearchClient esClient;
    @Test
    public void test2() {
//        GetResponse getResponse = esClient.prepareGet("ecommerce","null","4AFCXIsBSmUMdy7Bhcsy").get();
        GetRequest request = GetRequest.of(m ->
                m.index("ecommerce").id("4AFCXIsBSmUMdy7Bhcsy"));
        try {
            GetResponse getResponse = esClient.get(request, Insurance.class);
            System.out.println(getResponse);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
