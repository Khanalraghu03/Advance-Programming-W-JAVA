package inClassCode;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

public class ScrollBarInTableDemo extends Application {

    private TableView<Data> table1 = new TableView<>(); // table with scrollbars
    private TableView<Data> table2 = new TableView<>(); // table without scrollbars

    private final ObservableList<Data> data =
            FXCollections.observableArrayList(
                    new Data( LocalDate.of(2015, Month.JANUARY, 10), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 11), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 12), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 13), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 14), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 15), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 16), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 17), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 18), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 19), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 20), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 21), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 22), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 23), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 24), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 25), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 26), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 27), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 28), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 29), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 30), 10.0, 20.0, 30.0)

            );

    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        stage.setTitle("Table View Sample");
        stage.setWidth(800);
        stage.setHeight(800);

        // setup table columns
        setupTableColumns( table1);
        setupTableColumns( table2);

        // fill tables with data
        table1.setItems(data);
        table1.setTableMenuButtonVisible(true);

        // create container
        HBox hBox = new HBox();
        hBox.getChildren().addAll( table1, table2);

        ((Group) scene.getRoot()).getChildren().addAll( hBox);

        stage.setScene(scene);
        stage.show();

        ScrollBar table1HorizontalScrollBar = findScrollBar( table1, Orientation.HORIZONTAL);
        ScrollBar table1VerticalScrollBar = findScrollBar( table1, Orientation.VERTICAL);

        // this doesn't work:
        table1HorizontalScrollBar.setVisible(false);
        table1VerticalScrollBar.setVisible(false);

        ScrollBar table2HorizontalScrollBar = findScrollBar( table2, Orientation.HORIZONTAL);
        ScrollBar table2VerticalScrollBar = findScrollBar( table2, Orientation.VERTICAL);

        // this doesn't work:
        table2HorizontalScrollBar.setVisible(true);
        table2VerticalScrollBar.setVisible(true);

        // enforce layout to see if anything has an effect
        VirtualFlow flow1 = (VirtualFlow) table1.lookup(".virtual-flow");
        flow1.requestLayout();

        VirtualFlow flow2 = (VirtualFlow) table2.lookup(".virtual-flow");
        flow2.requestLayout();

    }

    /**
     * Primary table column mapping.
     */
    private void setupTableColumns( TableView table) {


        TableColumn<Data, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(120);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Data, Double> value1Col = new TableColumn<>("Value 1");
        value1Col.setPrefWidth(90);
        value1Col.setCellValueFactory(new PropertyValueFactory<>("value1"));

        TableColumn<Data, Double> value2Col = new TableColumn<>("Value 2");
        value2Col.setPrefWidth(90);
        value2Col.setCellValueFactory(new PropertyValueFactory<>("value2"));

        TableColumn<Data, Double> value3Col = new TableColumn<>("Value 3");
        value3Col.setPrefWidth(90);
        value3Col.setCellValueFactory(new PropertyValueFactory<>("value3"));

        table.getColumns().addAll( dateCol, value1Col, value2Col, value3Col);


    }

    /**
     * Find the horizontal scrollbar of the given table.
     * @param table
     * @return
     */
    private ScrollBar findScrollBar(TableView<?> table, Orientation orientation) {

        // this would be the preferred solution, but it doesn't work. it always gives back the vertical scrollbar
        //      return (ScrollBar) table.lookup(".scroll-bar:horizontal");
        //
        // => we have to search all scrollbars and return the one with the proper orientation

        Set<Node> set = table.lookupAll(".scroll-bar");
        for( Node node: set) {
            ScrollBar bar = (ScrollBar) node;
            if( bar.getOrientation() == orientation) {
                return bar;
            }
        }

        return null;

    }

    /**
     * Data for primary table rows.
     */
    public static class Data {

        private final ObjectProperty<LocalDate> date;
        private final SimpleDoubleProperty value1;
        private final SimpleDoubleProperty value2;
        private final SimpleDoubleProperty value3;

        public Data( LocalDate date, double value1, double value2, double value3) {

            this.date = new SimpleObjectProperty<LocalDate>( date);

            this.value1 = new SimpleDoubleProperty( value1);
            this.value2 = new SimpleDoubleProperty( value2);
            this.value3 = new SimpleDoubleProperty( value3);
        }

        public final ObjectProperty<LocalDate> dateProperty() {
            return this.date;
        }
        public final LocalDate getDate() {
            return this.dateProperty().get();
        }
        public final void setDate(final LocalDate date) {
            this.dateProperty().set(date);
        }
        public final SimpleDoubleProperty value1Property() {
            return this.value1;
        }
        public final double getValue1() {
            return this.value1Property().get();
        }
        public final void setValue1(final double value1) {
            this.value1Property().set(value1);
        }
        public final SimpleDoubleProperty value2Property() {
            return this.value2;
        }
        public final double getValue2() {
            return this.value2Property().get();
        }
        public final void setValue2(final double value2) {
            this.value2Property().set(value2);
        }
        public final SimpleDoubleProperty value3Property() {
            return this.value3;
        }
        public final double getValue3() {
            return this.value3Property().get();
        }
        public final void setValue3(final double value3) {
            this.value3Property().set(value3);
        }


    }
}
