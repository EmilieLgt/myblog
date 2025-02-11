package org.wildcodeschool.myblog.mapper;

import org.springframework.stereotype.Component;
import org.wildcodeschool.myblog.dto.ArticleAuthorDTO;
import org.wildcodeschool.myblog.dto.ArticleCreateDTO;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    public ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());

        if (article.getCategory() != null) {
            articleDTO.setCategoryName(article.getCategory().getName());
        }

        if (article.getImages() != null) {
            articleDTO.setImageUrls(article.getImages().stream()
                    .map(Image::getUrl)
                    .collect(Collectors.toList()));
        }

        if (article.getArticleAuthors() != null) {
            articleDTO.setAuthors(article.getArticleAuthors().stream()
                    .filter(articleAuthor -> articleAuthor.getAuthor() != null)
                    .map(articleAuthor -> {
                        ArticleAuthorDTO articleAuthorDTO = new ArticleAuthorDTO();
                        articleAuthorDTO.setId(articleAuthor.getId());
                        articleAuthorDTO.setArticleId(article.getId());
                        articleAuthorDTO.setAuthorId(articleAuthor.getAuthor().getId());
                        articleAuthorDTO.setContribution(articleAuthor.getContribution());
                        return articleAuthorDTO;
                    })
                    .collect(Collectors.toList()));
        }

        return articleDTO;
    }

    public Article convertToEntity(ArticleCreateDTO articleCreateDTO) {
        Article article = new Article();
        article.setTitle(articleCreateDTO.getTitle());
        article.setContent(articleCreateDTO.getContent());
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        if (articleCreateDTO.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(articleCreateDTO.getCategoryId());
            article.setCategory(category);
        }

        if (articleCreateDTO.getImageUrls() != null) {
            List<Image> images = articleCreateDTO.getImageUrls().stream()
                    .map(url -> {
                        Image image = new Image();
                        image.setUrl(url);
                        return image;
                    })
                    .collect(Collectors.toList());
            article.setImages(images);
        }

        if (articleCreateDTO.getAuthors() != null) {
            List<ArticleAuthor> articleAuthors = articleCreateDTO.getAuthors().stream()
                    .map(authorDTO -> {
                        ArticleAuthor articleAuthor = new ArticleAuthor();
                        Author author = new Author();
                        author.setId(authorDTO.getAuthorId());
                        articleAuthor.setAuthor(author);
                        articleAuthor.setContribution(authorDTO.getContribution());
                        return articleAuthor;
                    })
                    .collect(Collectors.toList());
            article.setArticleAuthors(articleAuthors);
        }

        return article;
    }
}