package irrigation.abox;

import java.util.*;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

public abstract class AbstractOntClass
{
    protected HashMap<String,Statement> statements = new HashMap<String,Statement>();
    protected Model model = null;
    protected Individual individual = null;
    
    public List<Statement> getStatements()
    {
        ArrayList <Statement> statementsList = new ArrayList<Statement>(statements.values());
        return statementsList;
    }

    public Individual getIndividual()
    {
        return individual;
    }
    
    public void removeExistingStatement(String key)
    {
        if(statements.containsKey(key))
        {
            if(model.contains(statements.get(key)))
            {
                model.remove(statements.get(key));
            }
            statements.remove(key);
        }
    }
}
