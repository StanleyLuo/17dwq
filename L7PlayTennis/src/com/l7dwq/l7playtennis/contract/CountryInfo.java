package com.l7dwq.l7playtennis.contract;

import java.util.List;

public class CountryInfo extends BaseRegionInfo{

	private List<ProvinceInfo> province;
	
	public List<ProvinceInfo> getSubordinates(){
		return province;
	}
	
	public static class ProvinceInfo extends BaseRegionInfo{
		
		private List<CityInfo> city;
		
		public List<CityInfo> getSubordinates(){
			return city;
		}
		
		public static class CityInfo extends BaseRegionInfo{
			private List<DistrictInfo> area;
			
			public List<DistrictInfo> getSubordinates(){
				return area;
			}
			public static class DistrictInfo extends BaseRegionInfo{

				private List<StreetInfo> street;
				
				public List<StreetInfo> getStreets(){
					return street;
				}
				@Override
				public List<StreetInfo> getSubordinates() {
					return street;
				}
				
				public static  class StreetInfo extends BaseRegionInfo {

					@Override
					public List<BaseRegionInfo> getSubordinates() {
						return null;
					}

				}

			}
		}
	}
}
