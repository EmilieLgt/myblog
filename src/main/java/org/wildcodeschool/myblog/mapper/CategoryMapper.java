package org.wildcodeschool.myblog.mapper;

import org.springframework.stereotype.Component;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.dto.CategoryDTO;
import org.wildcodeschool.myblog.model.Category;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.getId(category.getCategoryId());

        if (category.getArticles() != null) {
            categoryDTO.setArticles(category.getArticles().stream()
                    .map(article -> {
                        ArticleDTO articleDTO = new ArticleDTO();
                        articleDTO.setId(article.getId());
                        articleDTO.setTitle(article.getTitle());
                        articleDTO.setContent(article.getContent());
                        articleDTO.setUpdatedAt(article.getUpdatedAt());
                        if (article.getCategory() != null) {
                            articleDTO.setCategoryName(article.getCategory().getName());
                        }
                        return articleDTO;
                    })
                    .collect(Collectors.toList()));
        }
        return categoryDTO;

    }
}
