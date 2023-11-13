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
  private final int CARD_WIDTH = 400;
  private final int CARD_HEIGHT = 300;


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

    // Create a red border rectangle
    Rectangle borderRectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
    borderRectangle.setStroke(Color.RED);
    borderRectangle.setFill(Color.TRANSPARENT);

    // Create a gray background rectangle
    Rectangle backgroundRectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
    backgroundRectangle.setFill(Color.GREY);

    // Create a snapshot of the scene with the gray background and red border
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.TRANSPARENT); // Make the background transparent
    writableImage = backgroundRectangle.snapshot(params, writableImage);
    backgroundRectangle.setStroke(
        Color.TRANSPARENT); // Clear the border of the background rectangle

    // Create a JavaFX scene and add the border rectangle
    backgroundRectangle.setStroke(Color.RED);
    backgroundRectangle.setStrokeWidth(1.0); // Set the border width

    // Render the scene to the writable image
    backgroundRectangle.snapshot(params, writableImage);

    return writableImage;
  }

  private ImageView addTextToImage(Image image, String text) {
    ImageView imageView = new ImageView(image);

    Text textNode = new Text(text);
    textNode.setFont(Font.font("Verdana", 20));
    textNode.setWrappingWidth(CARD_WIDTH - 20);

    textNode.setFill(Color.RED);
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
