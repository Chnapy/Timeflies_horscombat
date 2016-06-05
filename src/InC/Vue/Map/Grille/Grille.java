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
import InC.Vue.Map.Grille.Tilemap.TileMap;
import InC.Vue.Map.Grille.Tilemap.TilePolygon;
import Main.Vue.Vue;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

/**
 * Grille.java
 *
 */
public class Grille extends StackMap<TileMap, EntiteMap, TilePolygon, EntiteSprite> {

	private static final double MAX_SCROLL = 256, MIN_SCROLL = 64, INITIAL_SCALE = 1;

	public final EffetsMap effetsMap;
	private final MicroHUDMap mhudmap;

	public Grille() {
		super(new TileMap(), new EntiteMap());
		setId("grille");
		setAlignment(Pos.CENTER_LEFT);
		setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		
		effetsMap = new EffetsMap();
		getChildren().add(effetsMap);

		mhudmap = new MicroHUDMap();
		getChildren().add(mhudmap);

		setOnScroll((e) -> {
			double s = tmap.getScaleX() + (double) e.getDeltaY() / 1000;
			scale(s);
		});
		scale(INITIAL_SCALE);
	}

	public final void scale(double s) {
		if (AbstractMap.TILE_WIDTH * s > MAX_SCROLL) {
			s = MAX_SCROLL / AbstractMap.TILE_WIDTH;
		} else if (AbstractMap.TILE_WIDTH * s < MIN_SCROLL) {
			s = MIN_SCROLL / AbstractMap.TILE_WIDTH;
		}
		tmap.setScaleX(s);
		tmap.setScaleY(s);
		emap.setScaleX(s);
		emap.setScaleY(s);
		effetsMap.setScaleX(s);
		effetsMap.setScaleY(s);
	}

	public void initScale() {
		Platform.runLater(() -> {
			double s = Vue.PRIMARYSTAGE.getWidth() / tmap.getWidth();
			scale(s);
		});
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
			setMHUDlayoutX(ehud, es);
		});

		emap.scaleYProperty().addListener((ov, t, t1) -> {
			setMHUDlayoutY(ehud, es);
		});

		mhudmap.getChildren().add(ehud);
		Platform.runLater(() -> {
			setMHUDlayoutX(ehud, es);
			setMHUDlayoutY(ehud, es);
		});
	}

	private void setMHUDlayoutX(EntiteHUD ehud, EntiteSprite es) {
		ehud.setLayoutX(
				es.localToScene(es.getBoundsInLocal()).getMinX()
				- (localToScene(getBoundsInLocal()).getWidth() - getWidth()) / 2
				- localToScene(getBoundsInLocal()).getMinX()
				- ehud.left.getWidth()
		);
	}

	private void setMHUDlayoutY(EntiteHUD ehud, EntiteSprite es) {
		ehud.setLayoutY(
				es.localToScene(es.getBoundsInLocal()).getMinY()
				- mhudmap.localToScene(mhudmap.getBoundsInLocal()).getMinY()
				- ehud.top.getHeight()
		);
	}

}
