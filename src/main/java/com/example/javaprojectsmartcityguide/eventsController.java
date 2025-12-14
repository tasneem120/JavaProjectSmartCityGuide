package com.example.javaprojectsmartcityguide;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;

import java.io.IOException;

public class eventsController {

    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img3;
    @FXML private ImageView img4;
    @FXML private ImageView img5;
    @FXML private ImageView img6;

    @FXML private DatePicker dateFilter; // لو عندك فلتر

    // تهيئة — Scene Builder يناديها أو استخدمي @FXML initialize()
    @FXML
    private void initialize() {
        // ضعِ مسارات الصور (لو لم تضعيهم في FXML)
        try {
            img1.setImage(new Image(getClass().getResourceAsStream("Nouvel An.jpg")));
            img2.setImage(new Image(getClass().getResourceAsStream("65c04bc8381f273edc267c5263e6005c.jpg")));
            img3.setImage(new Image(getClass().getResourceAsStream("Paris Marathon.jpg")));
            img4.setImage(new Image(getClass().getResourceAsStream("Paris Jazz Festival.jpg")));
            img5.setImage(new Image(getClass().getResourceAsStream("Avignon Theatre Festival.jpg")));
            img6.setImage(new Image(getClass().getResourceAsStream("Halloween in Paris.jpg")));
        } catch (Exception e) {
            // ممكن يكون تم تعيين الصور في FXML — تجاهلي
        }

        // نضيف hover effect لكل صورة
        addHover(img1);
        addHover(img2);
        addHover(img3);
        addHover(img4);
        addHover(img5);
        addHover(img6);
    }

    // دالة عامة لإضافة Hover effect لأي Node (هنا ImageView)
    private void addHover(Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });
        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });
    }
    // زرار 1
    public void showDetails1(ActionEvent e) {
        openDetails("New Year's Eve",
                " Day : 31 December – 1 January",
                " started at : 10:00 pm – 12:30 am",
                "Nouvel An.jpg",
                "Celebrate the start of the New Year in Paris and other major cities with spectacular fireworks, light shows, and live music. The Eiffel Tower becomes the centerpiece of dazzling illuminations, while Champs-Élysées and city squares host street performances, parties, and countdown events. A perfect blend of culture, festivity, and nightlife!" +
                        "A huge fireworks show at Eiffel Tower during New Year.");
    }

    // زرار 2
    public void showDetails2(ActionEvent e) {
        openDetails("Fête des Lumières",
                " Day : 8–11 December",
                " Started at : 6:00 pm",
                "65c04bc8381f273edc267c5263e6005c.jpg",
                "Lyon transforms into a magical city of lights for four nights. Artistic light installations illuminate historical buildings, squares, and bridges, creating an unforgettable visual experience. Locals and tourists stroll through illuminated streets, enjoy outdoor art shows, and take stunning photos. It’s one of Europe’s most enchanting winter festivals.");
    }

    // زرار 3
    public void showDetails3(ActionEvent e) {
        openDetails("Paris Marathon",
                " In April (varies)",
                "Started at : 8:30 am",
                "Paris Marathon.jpg",
                "One of the largest marathons in Europe." +
                        "oin thousands of runners from all over the world in one of the most iconic city marathons. The route passes through famous landmarks like the Champs-Élysées, Louvre, and Notre-Dame, offering a scenic run through the heart of Paris. Spectators cheer along the streets, creating a vibrant atmosphere for participants and visitors alike.");
    }

    // زرار 4
    public void showDetails4(ActionEvent e) {
        openDetails("Paris Jazz Festival",
                "Day : 30 June – 1 July",
                " Started at : 11:00 pm",
                "Paris Jazz Festival.jpg",
                "Experience Paris in full musical swing during the annual Jazz Festival. Held in beautiful city parks, the festival hosts live performances by international and local jazz artists. Visitors enjoy relaxing afternoons with smooth jazz, picnic areas, and workshops for aspiring musicians. A perfect cultural escape for music lovers of all ages.");
    }

    // زرار 5
    public void showDetails5(ActionEvent e) {
        openDetails("Avignon Theatre Festival",
                " Day : 7 July – 30 July",
                " Started at 3:00 pm",
                "Avignon Theatre Festival.jpg",
                "One of the world’s largest theatre festivals, Avignon transforms its historic streets into a stage. From classical plays to modern experimental theatre, performances take place in open-air squares, courtyards, and historic venues. Street artists, workshops, and cultural exhibitions make the festival a full sensory experience, attracting theatre.");
    }

    // زرار 6
    public void showDetails6(ActionEvent e) {
        openDetails("Halloween in Paris",
                " Day : 31 October",
                " Started at : 6:00 pm",
                "Halloween in Paris.jpg",
                "Paris comes alive with spooky fun on Halloween night. Costume parties, themed events, haunted houses, and parades take over streets and clubs. Families enjoy trick-or-treating in select neighborhoods, while young adults attend vibrant parties in famous venues. It’s a mix of mystery, fun, and French flair for this international celebration.");
    }


    // الدالة الأساسية اللي بتفتح صفحة التفاصيل
    private void openDetails(String title, String date, String time, String image, String description) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojectsmartcityguide/events-details.fxml")
            );
            Parent root = loader.load();

            // نوصّل البيانات لصفحة التفاصيل
            detailsController controller = loader.getController();
            controller.setData(title, date, time, image, description);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void backWelcome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("welcome .fxml"))));
    }
}

