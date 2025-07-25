package integration;

import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = alten.api.AltenApp.class)
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetProduct() throws Exception {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Test Product");
        request.setDescription("Description");
        request.setPrice(99.99);
        request.setQuantity(10);

        // Create product
        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductResponseDTO created = objectMapper.readValue(response, ProductResponseDTO.class);

        // Get product by ID
        mockMvc.perform(get("/products/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductRequestDTO createRequest = new ProductRequestDTO();
        createRequest.setName("Initial Product");
        createRequest.setDescription("Initial Desc");
        createRequest.setPrice(50.0);
        createRequest.setQuantity(5);

        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductResponseDTO created = objectMapper.readValue(response, ProductResponseDTO.class);

        ProductRequestDTO updateRequest = new ProductRequestDTO();
        updateRequest.setName("Updated Product");
        updateRequest.setDescription("Updated Desc");
        updateRequest.setPrice(100.0);
        updateRequest.setQuantity(15);

        mockMvc.perform(put("/products/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("To Delete");
        request.setDescription("Delete me");
        request.setPrice(20.0);
        request.setQuantity(1);

        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductResponseDTO created = objectMapper.readValue(response, ProductResponseDTO.class);

        mockMvc.perform(delete("/products/" + created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/products/" + created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateQuantity() throws Exception {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Quantity Test");
        request.setDescription("Test Quantity");
        request.setPrice(10.0);
        request.setQuantity(5);

        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductResponseDTO created = objectMapper.readValue(response, ProductResponseDTO.class);

        mockMvc.perform(patch("/products/" + created.getId() + "/quantity")
                        .param("quantity", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(30));
    }

    @Test
    void testListProducts() throws Exception {
        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}