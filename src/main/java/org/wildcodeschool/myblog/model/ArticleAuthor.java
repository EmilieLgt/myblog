package org.wildcodeschool.myblog.model;

import jakarta.persistence.*;

@Entity
public class ArticleAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(nullable = false, length = 50)
    private String contribution;

    // Getters
    public Long getId() {
        return id;
    }

    public Article getArticle() {
        return article;
    }

    public Author getAuthor() {
        return author;
    }

    public String getContribution() {
        return contribution;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }
}