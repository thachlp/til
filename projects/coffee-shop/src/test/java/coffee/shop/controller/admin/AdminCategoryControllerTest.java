package coffee.shop.controller.admin;

import coffee.shop.dto.request.CategoryRequest;
import coffee.shop.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminCategoryController.class)
@AutoConfigureMockMvc
class AdminCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCategory() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new CategoryRequest("")))
                )
                .andExpect(status().is(201));
    }

    @Test
    void updateCategory() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/admin/categories/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new CategoryRequest("Coffee")))
                )
                .andExpect(status().is(200));
    }

    @Test
    void deleteCategory() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/admin/categories/1")
                )
                .andExpect(status().is(200));
    }
}
