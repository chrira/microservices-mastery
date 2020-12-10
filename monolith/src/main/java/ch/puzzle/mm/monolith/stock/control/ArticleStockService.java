package ch.puzzle.mm.monolith.stock.control;

import ch.puzzle.mm.monolith.exception.ArticleOutOfStockException;
import ch.puzzle.mm.monolith.stock.entity.ArticleStock;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ArticleStockService {

    private final Logger log = LoggerFactory.getLogger(ArticleStockService.class.getName());

    @Inject
    ArticleStockRepository articleStockRepository;

    @Traced
    public void orderArticle(Long articleId, int amount) throws ArticleOutOfStockException {
        ArticleStock articleStock = articleStockRepository.find("article_id", articleId).singleResult();
        if (articleStock.getCount() < amount)
            throw new ArticleOutOfStockException("Article with id " + articleId + " is out of stock.");
        articleStock.setCount(articleStock.getCount() - amount);
        log.info("Article with id {} processed", articleId);
    }

    public Optional<ArticleStock> findById(Long articleId) {
        return Optional.of(articleStockRepository.findById(articleId));
    }
}