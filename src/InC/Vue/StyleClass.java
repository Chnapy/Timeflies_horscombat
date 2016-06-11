/*
 * 
 * 
 * 
 */
package InC.Vue;

import Serializable.InCombat.TypeCarac;
import java.util.HashMap;

/**
 * StyleClass.java
 *
 */
public class StyleClass {

	public static final HashMap<TypeCarac, String> CARAC_CLASS = new HashMap();
	static {
		CARAC_CLASS.put(TypeCarac.VITALITE, "vitalite");
		CARAC_CLASS.put(TypeCarac.TEMPSACTION, "tempsa");
		CARAC_CLASS.put(TypeCarac.TEMPSSUP, "tempss");
		CARAC_CLASS.put(TypeCarac.VITESSE, "vitesse");
		CARAC_CLASS.put(TypeCarac.FATIGUE, "fatigue");
		CARAC_CLASS.put(TypeCarac.INITIATIVE, "initiative");
		CARAC_CLASS.put(TypeCarac.NIVEAU, "niveau");
		CARAC_CLASS.put(TypeCarac.COOLDOWN, "cooldown");
	}

}
