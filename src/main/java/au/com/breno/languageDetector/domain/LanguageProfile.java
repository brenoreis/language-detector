package au.com.breno.languageDetector.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by brenoreis on 25/07/2016.
 */

@Entity
@Table(
        name = "language_profile_language_ngram",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"language", "ngram"})}
)
public class LanguageProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="language")
    private Language language;

    @Column(nullable = false)
    private String ngram;

    @Column(nullable = false)
    private Integer rank;

    protected LanguageProfile() {}

    public LanguageProfile(Language language, String ngram, Integer rank) {
        this.language = language;
        this.ngram = ngram;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getNgram() {
        return ngram;
    }

    public void setNgram(String ngram) {
        this.ngram = ngram;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
