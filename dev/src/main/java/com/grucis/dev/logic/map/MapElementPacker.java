package com.grucis.dev.logic.map;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;

public final class MapElementPacker {

  private Map<Integer, Set<Integer>> elementSetMap = new TreeMap<Integer, Set<Integer>>();
  private Map<Integer, Set<Integer>> usageMap;
  private Set<Integer> commonSet;
  private List<Set<Integer>> clusters;
  private Map<Integer, Integer> clusterMap;


  public void addElementSet(int id, Set<Integer> elementSet) {
    Set<Integer> existing = elementSetMap.get(id);
    if(existing == null) {
      elementSetMap.put(id, new TreeSet<Integer>(elementSet));
    } else {
      existing.addAll(elementSet);
    }
  }

  private void calculateUsage() {
    usageMap = new TreeMap<Integer, Set<Integer>>();
    clusters = new ArrayList<Set<Integer>>();
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
  }

  private boolean calculateCluster() {
    List<Set<Integer>> testClusters = new ArrayList<Set<Integer>>();
    Map<Integer, Integer> testClusterMap = new TreeMap<Integer, Integer>();

    for(Map.Entry<Integer, Set<Integer>> entry : elementSetMap.entrySet()) {
      boolean foundCluster = false;
      Set<Integer> elements = new TreeSet<Integer>(entry.getValue());
      elements.removeAll(commonSet);
      if(elements.size() > commonSet.size()) return false;
      int index = 0;
      for(Set<Integer> cluster : testClusters) {
        if(CollectionUtils.containsAny(cluster, elements)) {
          cluster.addAll(elements);
          if(cluster.size() > commonSet.size()) return false;
          testClusterMap.put(entry.getKey(), index);
          foundCluster = true;
          break;
        }
        index++;
      }
      if(!foundCluster) {
        testClusters.add(elements);
        testClusterMap.put(entry.getKey(), index);
      }
    }

    clusters = testClusters;
    clusterMap = testClusterMap;
    return true;
  }

  private void calculateCommonSet() {
    commonSet = new TreeSet<Integer>();
    List<Integer> elements = new ArrayList<Integer>(usageMap.keySet());
    Collections.sort(elements, new Comparator<Integer>() {
      @Override
      public int compare(Integer us1, Integer us2) {
        return Integer.compare(usageMap.get(us2).size(), usageMap.get(us1).size());
      }
    });
    for(int element : elements) {
      commonSet.add(element);
      if(calculateCluster()) return;
    }

  }

  public void pack() {
    calculateUsage();
    calculateCommonSet();
  }



}
