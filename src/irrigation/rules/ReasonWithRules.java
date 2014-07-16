package irrigation.rules;

import irrigation.rules.SensorAlarm;

import java.net.MalformedURLException;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class ReasonWithRules {

	/*
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		
		Model model = ModelFactory.createDefaultModel();
		model.read("file:result/orchard.nt","RDF/XML");
		
		InfModel infModel = ModelFactory.createRDFSModel(model);
		
		Resource irrigation = infModel.getResource("http://www.semanticweb.org/ece8486class/ontologies/2014/6/irrigation#owns");
		Resource vcard = infModel.getResource("http://www.w3.org/2006/vcard/ns#VCard1405462881088");
		BuiltinRegistry.theRegistry.register(new SensorAlarm());
		
		Model postRuleModel = processRules("rules/irrigation.rules", infModel);
		
		printStatements(postRuleModel, vcard, null, null);
		printStatements(postRuleModel, irrigation, null, null);
		System.out.println("<----- Jena Done ------>");
	}
	
	public static void printStatements(Model m, Resource s, Property p, Resource o) {
		PrintUtil.registerPrefix("x", "http://www.semanticweb.org/ece8486class/ontologies/2014/6/irrigation#");
		for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
			Statement stmt = i.nextStatement();
			System.out.println(" - " + PrintUtil.print(stmt));
		}
	}
	
	public static Model processRules(String fileloc, InfModel modelIn) {
		// create a simple model; create a resource and add rules from a file
		Model m = ModelFactory.createDefaultModel();
		Resource configuration = m.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleSet, fileloc);
		
		// create an instance of a reasoner
		Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);
		
		// Now with the raw data model and the reasoner, create an InfModel
		InfModel infmodel = ModelFactory.createInfModel(reasoner, modelIn);
		
		return infmodel;
	}
	
}
