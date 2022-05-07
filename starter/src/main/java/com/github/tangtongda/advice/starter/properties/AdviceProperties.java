package com.github.tangtongda.advice.starter.properties;

import java.util.ArrayList;
import java.util.List;

public class AdviceProperties {

  /** exclude packages */
  private List<String> excludePackages = new ArrayList<>();

  /** exclude class */
  private List<String> excludeClasses = new ArrayList<>();

  public List<String> getExcludePackages() {
    return excludePackages;
  }

  public void setExcludePackages(List<String> excludePackages) {
    this.excludePackages = excludePackages;
  }

  public List<String> getExcludeClasses() {
    return excludeClasses;
  }

  public void setExcludeClasses(List<String> excludeClasses) {
    this.excludeClasses = excludeClasses;
  }
}
