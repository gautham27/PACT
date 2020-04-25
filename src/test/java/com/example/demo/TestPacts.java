//package com.example.demo;
//
//
//import au.com.dius.pact.provider.junit.Consumer;
//import au.com.dius.pact.provider.junit.PactRunner;
//import au.com.dius.pact.provider.junit.Provider;
//import au.com.dius.pact.provider.junit.State;
//import au.com.dius.pact.provider.junit.loader.PactBroker;
//import au.com.dius.pact.provider.junit.loader.PactFolder;
//import au.com.dius.pact.provider.junit.target.HttpTarget;
//import au.com.dius.pact.provider.junit.target.Target;
//import au.com.dius.pact.provider.junit.target.TestTarget;
//
//import org.junit.Assert;
//import org.junit.runner.RunWith;
//
//@RunWith(PactRunner.class)
//@Provider("test_provider1")
//@Consumer("test_consumer1")
//@PactFolder("C:\\Users\\Downloads\\PactDemo\\target\\pacts")
//public class TestPacts {
//
//  @TestTarget
//  public final Target target = new HttpTarget(8080);
//
//  @State("test state")
//  public void MyState() {
//      // Prepare service before interaction that require "default" state
//      // ...
//
//
//  }
//}