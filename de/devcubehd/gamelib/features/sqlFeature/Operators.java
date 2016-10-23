package de.devcubehd.gamelib.features.sqlFeature;

import java.util.HashMap;
import java.util.Map;

public class Operators {
	
	public enum DataTypes {
		
		Text(1), Char(2), Int(3), Decimal(4), Float(5), Double(6), Boolean(7), Date(8), Time(9), Varchar(10);

		private static final Map<Integer, DataTypes> valueList;
		private final int value;

		static {
			
			valueList = new HashMap<Integer, DataTypes>();
			
			for (DataTypes result : values()) {
				valueList.put(Integer.valueOf(result.value), result);
			}
			
		}

		private DataTypes(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static DataTypes getResult(int value) {
			return (DataTypes) valueList.get(Integer.valueOf(value));
		}
		
	}

}