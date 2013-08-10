package com.grucis.dev.logic.map;

import java.util.*;

public final class MapElementPacker {

  private Map<Integer, Set<Integer>> elementSetMap = new TreeMap<Integer, Set<Integer>>();
  private Map<Integer, Set<Integer>> usageMap;


  public void addElementSet(int id, Set<Integer> elementSet) {
    elementSetMap.put(id, elementSet);
  }

  public void addElementSets(Map<Integer, Set<Integer>> elementSets) {
    elementSetMap.putAll(elementSets);
  }

  public Map<Integer, Set<Integer>> calculateUsageMap() {
    usageMap = new TreeMap<Integer, Set<Integer>>();
    for(Map.Entry<Integer, Set<Integer>> entry : elementSetMap.entrySet()) {
      int map = entry.getKey();
      Set<Integer> elementSet = entry.getValue();
      for(int element : elementSet) {
        Set<Integer> usage = usageMap.get(element);
        if(usage == null) {
          usage = new TreeSet<Integer>();
          usageMap.put(element, usage);
        }
        usage.add(map);
      }
    }
    return usageMap;
  }



}
