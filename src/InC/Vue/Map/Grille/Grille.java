/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille;

import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Map.Tuile;
import InC.Vue.Map.Grille.EffetsMap.EffetsMap;
import InC.Vue.Map.Grille.Entitemap.EntiteMap;
import InC.Vue.Map.Grille.Entitemap.EntiteSprite;
import InC.Vue.Map.Grille.MicroHUD.EntiteHUD;
import InC.Vue.Map.Grille.MicroHUD.MicroHUDMap;
import InC.Vue.Map.Grille.NotificationMap.NotificationMap;
import InC.Vue.Map.Grille.Tilemap.TileMap;
import InC.Vue.Map.Grille.Tilemap.TilePolygon;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 * Grille.java
 *
 */
public class Grille extends StackMap<TileMap, EntiteMap, TilePolygon, EntiteSprite> {

	public final EffetsMap effetsMap;
	private final MicroHUDMap mhudmap;
	public final NotificationMap notifMap;

	public final Node[] scalables;
	public double scale;

	public Grille() {
		super(new TileMap(), new EntiteMap());
		setId("grille");
		setAlignment(Pos.CENTER_LEFT);
		setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		effetsMap = new EffetsMap();
		getChildren().add(effetsMap);

		mhudmap = new MicroHUDMap();
		getChildren().add(mhudmap);

		notifMap = new NotificationMap();
		getChildren().add(notifMap);

		scalables = new Node[]{
			tmap, emap, effetsMap, notifMap
		};
	}

	@Override
	public TilePolygon ajoutTuile(Tuile tuile, int x, int y) {
		TilePolygon tp = new TilePolygon(tuile);
		tmap.ajoutNode(tp, x, y);
		return tp;
	}

	@Override
	public EntiteSprite ajoutEntite(EntitePassive e) {
		EntiteSprite es = new EntiteSprite(e);
		emap.ajoutNode(es, e.getBinding().position.get().x, e.getBinding().position.get().y);
		placerMicroHUD(es.getEhud(), es);
		return es;
	}

	private void placerMicroHUD(EntiteHUD ehud, EntiteSprite es) {

		ehud.prefWidthProperty().bind(es.fitWidthProperty().multiply(emap.scaleXProperty())
				.add(ehud.left.widthProperty())
				.add(ehud.right.widthProperty())
		);
		ehud.prefHeightProperty().bind(es.fitHeightProperty().multiply(emap.scaleYProperty())
				.add(ehud.top.heightProperty())
		);

		emap.scaleXProperty().addListener((ov, t, t1) -> {
			setMHUDtranslateX(ehud, es);
		});

		emap.scaleYProperty().addListener((ov, t, t1) -> {
			setMHUDtranslateY(ehud, es);
		});

		es.translateXProperty().addListener((ov, t, t1) -> {
			setMHUDtranslateX(ehud, es);
			setMHUDtranslateY(ehud, es);
		});

		mhudmap.getChildren().add(ehud);
		Platform.runLater(() -> {
			setMHUDtranslateX(ehud, es);
			setMHUDtranslateY(ehud, es);
		});
	}

	private void setMHUDtranslateX(EntiteHUD ehud, EntiteSprite es) {
		ehud.setTranslateX(
				es.localToScene(es.getBoundsInLocal()).getMinX()
				- (localToScene(getBoundsInLocal()).getWidth() - getWidth()) / 2
				- localToScene(getBoundsInLocal()).getMinX()
				- ehud.left.getWidth()
		);
	}

	private void setMHUDtranslateY(EntiteHUD ehud, EntiteSprite es) {
		ehud.setTranslateY(
				es.localToScene(es.getBoundsInLocal()).getMinY()
				- mhudmap.localToScene(mhudmap.getBoundsInLocal()).getMinY()
				- ehud.top.getHeight()
		);
	}
	
	public TileMap getTileMap() {
		return tmap;
	}

}
