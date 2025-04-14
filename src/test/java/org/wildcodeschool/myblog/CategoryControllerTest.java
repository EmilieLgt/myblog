package org.wildcodeschool.myblog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.wildcodeschool.myblog.controller.CategoryController;
import org.wildcodeschool.myblog.dto.CategoryDTO;
import org.wildcodeschool.myblog.service.CategoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testGetAllCategories() throws Exception {
        // Arrange
        CategoryDTO category1 = new CategoryDTO();
        category1.setName("Category 1");

        CategoryDTO category2 = new CategoryDTO();
        category2.setName("Category 2");

        List<CategoryDTO> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        // Act & Assert
        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }

    @Test
    void testGetAllCategories_NoContent() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/category"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCategoryById_CategoryExists() throws Exception {
        // Arrange
        CategoryDTO category = new CategoryDTO();
        category.setName("Category 1");

        when(categoryService.getCategoryById(1L)).thenReturn(category);

        // Act & Assert
        mockMvc.perform(get("/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @Test
    void testGetCategoryById_CategoryNotFound() throws Exception {
        // Arrange
        when(categoryService.getCategoryById(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/category/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCategory() throws Exception {
        // Arrange
        CategoryDTO categoryToCreate = new CategoryDTO();
        categoryToCreate.setName("New Category");

        CategoryDTO createdCategory = new CategoryDTO();
        createdCategory.setName("New Category");

        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(createdCategory);

        // Act & Assert
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Category\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void testUpdateCategory_CategoryExists() throws Exception {
        // Arrange
        CategoryDTO categoryToUpdate = new CategoryDTO();
        categoryToUpdate.setName("Updated Category");

        CategoryDTO updatedCategory = new CategoryDTO();
        updatedCategory.setName("Updated Category");

        when(categoryService.updateCategory(eq(1L), any(CategoryDTO.class))).thenReturn(updatedCategory);

        // Act & Assert
        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Category\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void testUpdateCategory_CategoryNotFound() throws Exception {
        // Arrange
        when(categoryService.updateCategory(eq(99L), any(CategoryDTO.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/category/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Category\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategory_CategoryExists() throws Exception {
        // Arrange
        when(categoryService.deleteCategory(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCategory_CategoryNotFound() throws Exception {
        // Arrange
        when(categoryService.deleteCategory(99L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/category/99"))
                .andExpect(status().isNotFound());
    }
}