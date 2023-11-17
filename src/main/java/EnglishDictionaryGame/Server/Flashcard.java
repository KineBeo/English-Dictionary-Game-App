package EnglishDictionaryGame.Server;

import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Flashcard {

  private ImageView cardView;
  private String frontText;
  private Image frontImage;
  private String backText;
  private Image backImage;
  private boolean isShowingFront;
  private final int CARD_WIDTH = 325;
  private final int CARD_HEIGHT = 300;
  private final int BORDER_RADIUS = 30;
  private final Color BACKGROUND_COLOR = Color.GREY;
  private final Color BORDER_COLOR = Color.RED;
  private final Color TEXT_COLOR = Color.WHITE;
  private final int TEXT_SIZE = 20;
  private final String TEXT_FONT = "Verdana";

  public Flashcard() {
  }

  public Flashcard(String frontText, String backText) {
    this.frontText = frontText;
    this.backText = backText;
    frontImage = createCardFaceImage(frontText, false);
    backImage = createCardFaceImage(backText, true);
    cardView = new ImageView(frontImage);
    isShowingFront = true;
  }

  public void flipCardView() {
    this.cardView.setImage(isShowingFront ? backImage : frontImage);
    isShowingFront = !isShowingFront;
  }

  public void reloadData() {
    flipCardView();
    flipCardView();
  }

  public String getFrontText() {
    return frontText;
  }

  public void setFrontText(String text) {
    this.frontText = text;
    frontImage = createCardFaceImage(frontText, false);
  }

  public String getBackText() {
    return backText;
  }

  public void setBackText(String text) {
    this.backText = text;
    backImage = createCardFaceImage(backText, true);
  }

  public ImageView getCardView() {
    return cardView;
  }

  public boolean isShowingFront() {
    return isShowingFront;
  }

  private Image createCardFaceImage(String text, boolean isBackImage) {
    Image cardFaceImage = makeImage(text);
    if (isBackImage) {
      cardFaceImage = mirrorImage(cardFaceImage);
    }

    return cardFaceImage;
  }

  private Image makeImage(String text) {        // Create a group to hold both rectangles
    StackPane group = new StackPane();
    Rectangle rectangle = createRectangle();
    Text textNode = createTextNode(text);
    group.getChildren().addAll(rectangle, textNode);
    Image image = group.snapshot(null, null);
    return image;
  }

  private Rectangle createRectangle() {
    Rectangle rectangle = new Rectangle();
    rectangle.setWidth(CARD_WIDTH);
    rectangle.setHeight(CARD_HEIGHT);
    rectangle.setFill(BACKGROUND_COLOR);
    rectangle.setStroke(BORDER_COLOR);
    rectangle.setArcWidth(BORDER_RADIUS);
    rectangle.setArcHeight(BORDER_RADIUS);
    return rectangle;
  }

  private Text createTextNode(String text) {
    Text textNode = new Text(text);
    textNode.setFont(Font.font(TEXT_FONT, TEXT_SIZE));
    textNode.setFill(TEXT_COLOR);
    textNode.setTextAlignment(TextAlignment.CENTER);
    textNode.setWrappingWidth(CARD_WIDTH - 20);
    return textNode;
  }

  private Image mirrorImage(Image image) {
    ImageView mirroredImageView = new ImageView(image);
    mirroredImageView.setScaleY(-1);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(mirroredImageView);
    return stackPane.snapshot(null, null);
  }
}
