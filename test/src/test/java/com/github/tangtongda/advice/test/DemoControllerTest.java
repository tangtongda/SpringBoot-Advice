package com.github.tangtongda.advice.test;

import com.github.tangtongda.advice.test.controller.DemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:tangtongda@gmail.com">Tino_Tang</a>
 * @version 1.0.0-5/6/2022
 */
@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

  @Autowired private DemoController controller;

  @Autowired private MockMvc mockMvc;

  @Test
  void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  void getData() throws Exception {
    this.mockMvc.perform(get("/test/get")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void postData() throws Exception {
    this.mockMvc.perform(post("/test/post")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void getEntity() throws Exception {
    this.mockMvc.perform(get("/test/getEntity")).andDo(print()).andExpect(status().isOk());
  }
}
