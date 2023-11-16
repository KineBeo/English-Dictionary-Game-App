package EnglishDictionaryGame.Server;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Flashcard {

  private ImageView cardView;
  private String frontText;
  private Image frontImage;
  private String backText;
  private Image backImage;
  private boolean isShowingFront;
  private final int CARD_WIDTH = 325;
  private final int CARD_HEIGHT = 300;
  private final double BORDER_WIDTH = 2.0;
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

  public Image getBackImage() {
    return backImage;
  }

  public Image getFrontImage() {
    return frontImage;
  }

  public ImageView getCardView() {
    return cardView;
  }

  public boolean isShowingFront() {
    return isShowingFront;
  }

  private Image createCardFaceImage(String text, boolean isBackImage) {
    ImageView cardFaceImageView = new ImageView(makeBackgroundImage());
    cardFaceImageView.setFitWidth(CARD_WIDTH);
    cardFaceImageView.setFitHeight(CARD_HEIGHT);

    cardFaceImageView = addTextToImage(cardFaceImageView.getImage(), text);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(cardFaceImageView);
    Image cardFaceImage = stackPane.snapshot(null, null);
    if (isBackImage) {
      cardFaceImage = mirrorImage(cardFaceImage);
    }

    return cardFaceImage;
  }
  private Image makeBackgroundImage() {
    // Create a transparent image with a specified width and height
    WritableImage writableImage = new WritableImage(CARD_WIDTH, CARD_HEIGHT);


    Rectangle borderRectangle = createRectangle(Color.TRANSPARENT, BORDER_COLOR);
    setBorderAttributes(borderRectangle);


    Rectangle backgroundRectangle = createRectangle(BACKGROUND_COLOR, Color.TRANSPARENT);
    setBorderAttributes(backgroundRectangle);


    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.TRANSPARENT);

    // Render the scene to the writable image
    backgroundRectangle.snapshot(params, writableImage);

    return writableImage;
  }

  private Rectangle createRectangle(Color fill, Color stroke) {
    Rectangle rectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
    rectangle.setFill(fill);
    rectangle.setStroke(stroke);
    rectangle.setArcWidth(BORDER_RADIUS);
    rectangle.setArcHeight(BORDER_RADIUS);
    return rectangle;
  }

  private void setBorderAttributes(Rectangle rectangle) {
    rectangle.setStrokeWidth(BORDER_WIDTH);
  }

  private ImageView addTextToImage(Image image, String text) {
    ImageView imageView = new ImageView(image);

    Text textNode = new Text(text);
    textNode.setFont(Font.font(TEXT_FONT, TEXT_SIZE));
//    textNode.setWrappingWidth(CARD_WIDTH - 20);

    textNode.setFill(TEXT_COLOR);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(imageView, textNode);

    ImageView finalImageView = new ImageView(stackPane.snapshot(null, null));
    return finalImageView;
  }

  private Image mirrorImage(Image image) {
    ImageView mirroredImageView = new ImageView(image);
    mirroredImageView.setScaleY(-1);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(mirroredImageView);
    return stackPane.snapshot(null, null);
  }
}
