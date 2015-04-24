import com.mtgames.utils.Debug;
import com.mtgames.utils.LauncherBase;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

public class Test extends LauncherBase {

	static private ComboBox<Integer> scale;

	public static void main(String[] args) {
		scale = new ComboBox<>(FXCollections.observableArrayList(1, 2));
		scale.setValue(2);

		addOption("Scale", scale);
		addOption("Test", new RadioButton());
		addOption("Check box", new CheckBox());

		launch(args);
	}

	@Override protected void run() {
		System.setProperty("com.mtgames.scale", String.valueOf(scale.getValue()));

		Debug.log(String.valueOf(Integer.getInteger("com.mtgames.scale")), Debug.DEBUG);
	}
}
