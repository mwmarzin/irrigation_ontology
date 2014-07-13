package irrigation.rules;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.*;
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
        BuiltinRegistry.theRegistry.register(new IrrigationAlaram());
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
        
        return inferenceModel;
    }
}
