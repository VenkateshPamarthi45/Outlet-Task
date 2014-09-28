package com.couponduniatask;

import java.util.Comparator;

public class DistanceComparator  implements Comparator<Outlet> {
	   @Override
	   public int compare(Outlet a, Outlet b) {
	       return a.getDistance() < b.getDistance() ? -1 : a.getDistance() == b.getDistance() ? 0 : 1;
	   }
	}