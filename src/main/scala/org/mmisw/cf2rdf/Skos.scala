package org.mmisw.cf2rdf

import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.rdf.model.Resource
import org.apache.jena.vocabulary.OWL
import org.apache.jena.vocabulary.RDF
import org.apache.jena.vocabulary.RDFS
import org.apache.jena.vocabulary.XSD

/**
 * SKOS vocabulary elements. Adapted from the original java version in the old "watchdog" project.
 *
 * @todo move this object to a more appropriate place.
 *
 * @see http://www.w3.org/2004/02/skos/core#
 */
object Skos {
  private val model = ModelFactory.createDefaultModel()

  val NS = "http://www.w3.org/2004/02/skos/core#"

  def property(local: String) = model.createProperty(NS, local)

  val Concept = model.createResource(NS + "Concept", RDFS.Class)

  val prefLabel      = property("prefLabel")
  val altLabel       = property("altLabel")
  val hiddenLabel    = property("hiddenLabel")

  val definition     = property("definition")
  val note           = property("note")
  val changeNote     = property("changeNote")
  val editorialNote  = property("editorialNote")
  val example        = property("example")
  val historyNote    = property("historyNote")
  val scopeNote      = property("scopeNote")
  val hasTopConcept  = property("hasTopConcept")

  val broader        = property("broader")
  val narrower       = property("narrower")
  val related        = property("related")
  val exactMatch     = property("exactMatch")
  val closeMatch     = property("closeMatch")
  val broadMatch     = property("broadMatch")
  val narrowMatch    = property("narrowMatch")
  val relatedMatch   = property("relatedMatch")
  
  def createModel: Model = {
    val newModel = ModelFactory.createDefaultModel()
    newModel.setNsPrefix("skos", NS)
    newModel.add(model)
    //newModel.add(Skos.broader, OWL.inverseOf, Skos.narrower)
    //newModel.add(Skos.broader, RDF.`type`,  OWL.TransitiveProperty)
    //newModel.add(Skos.broader, RDFS.range,  OWL.TransitiveProperty)
    //newModel.add(Skos.narrower, RDF.`type`,  OWL.TransitiveProperty)
    //newModel.add(Skos.related, RDF.`type`, OWL.SymmetricProperty)
    newModel
  }

  def addConceptSubClass(model: Model, conceptUri: String) = {
    val conceptSubClass = model.createResource(conceptUri)
    model.add(model.createStatement(conceptSubClass, RDF.`type`, OWL.Class))
    model.add(model.createStatement(conceptSubClass, RDFS.subClassOf, Skos.Concept))
    conceptSubClass
  }

  def addDatatypeProperty(model: Model, conceptSubClass: Resource, propUri:String, propLabel:String) = {
    val prop = model.createProperty(propUri)
    model.add(model.createStatement(prop, RDF.`type`, OWL.DatatypeProperty))
    model.add(model.createStatement(prop, RDFS.domain, conceptSubClass))
    model.add(model.createStatement(prop, RDFS.range, XSD.xstring))
    if ( propLabel != null ){
      model.add(model.createStatement(prop, RDFS.label, propLabel))
    }
    prop
  }
}
