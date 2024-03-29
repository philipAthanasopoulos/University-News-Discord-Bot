package discord;

import domain.Article;
import scraper.UniversityNewsScraper;

import java.util.ArrayList;

/**
 * The News presenter is responsible for formatting the news articles for discord.
 * It uses MarkDown characters like "*" , ">" , "#" to format the messages for discord.
 * The News Presenter is the bridge between the scraper and the discord Bot.
 *
 * @author Philip Athanasopoulos
 */
public class DiscordNewsPresenter {
    private final int MAX_DISCORD_MESSAGE_LENGTH = 2000;
    private final UniversityNewsScraper scraper;

    public DiscordNewsPresenter(UniversityNewsScraper scraper) {
        this.scraper = scraper;
    }

    public String getArticleAsDiscordMessage(Article article) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("## ").append(article.getTitle()).append("  \n").append("> ").append(article.getContent()).append(" ").append("\n");

        if (stringBuilder.length() > MAX_DISCORD_MESSAGE_LENGTH) trimArticle(stringBuilder, article.getLink());

        return stringBuilder.toString();
    }

    public DiscordSlideShow getNewsAsDiscordSlideShow() {
        DiscordSlideShow slideShow = new DiscordSlideShow();
        for (Article article : scraper.getArticles())
            slideShow.addSlide(getArticleAsDiscordMessage(article));
        return slideShow;
    }

    public ArrayList<String> getNewsAsDiscordMessages() {
        ArrayList<String> messages = new ArrayList<>();
        for (Article article : scraper.getArticles())
            messages.add(getArticleAsDiscordMessage(article));
        return messages;
    }

    public String getLatestNewsAsDiscordMessage() {
        Article article = scraper.getLatestArticle();
        return getArticleAsDiscordMessage(article);
    }

    private void trimArticle(StringBuilder stringBuilder, String link) {
        String redirectMessage = " ***....[Read more](" + link + ")***";
        stringBuilder.delete(MAX_DISCORD_MESSAGE_LENGTH - redirectMessage.length(), stringBuilder.length());
        stringBuilder.append(redirectMessage);
    }
}
