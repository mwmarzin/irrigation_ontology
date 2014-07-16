package irrigation.rules;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.*;

public class IrrigationRuleController
{
    //TODO move this to a properties file
    public static final String RULES_URL = "file://";
    public static final String ABOX_URL = "file://";
    
    public IrrigationRuleController()
    {
        
    }

    public static void registerRules()
    {
        BuiltinRegistry.theRegistry.register(new SensorAlarm());
    }
    
    public static GenericRuleReasoner getRuleReasoner()
    {
        //URL rulesURL = new URL(RULES_URL);
        List<Rule> rules = Rule.rulesFromURL(RULES_URL);
        GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
        
        return reasoner;
    }
    
    public static InfModel getInterenceModel()
    {
        Model defaultModel = ModelFactory.createDefaultModel();
        GenericRuleReasoner reasoner = getRuleReasoner();
        InfModel inferenceModel = ModelFactory.createInfModel(reasoner, defaultModel);
        InputStream is = new ByteArrayInputStream(ABOX_URL.getBytes());
        
        inferenceModel.read(is, "N-TRIPLE");
        
        //TODO Call rules
        
        return inferenceModel;
    }
    /*
    public static void addAboxBasedOnRulesAndTBox(InfModel infModel,Model m) 
    {
        GenericRuleReasoner reasoner = (GenericRuleReasoner) infModel
                .getReasoner();
        Map<String, Rule> ruleMap = new HashMap<String, Rule>();
        
        List<Rule> rules = reasoner.getRules();
        for (int i = 0; i < rules.size(); i++) 
        {
            Rule rule = rules.get(i);
            List<TriplePattern> triples = SetUniqueList
                    .decorate(new ArrayList<TriplePattern>());
            
            ClauseEntry[] body = rule.getBody();
            addClauses(body, triples);
            for (int j = 0; j < triples.size(); j++) {
                TriplePattern tp = triples.get(j);
                Rule rule = new Rule();
                rulePOJO.setTp(tp);
                String predicate = tp.getPredicate().toString();

                ruleMap.put(predicate, rulePOJO);
            }
        }
    }
    */
}
