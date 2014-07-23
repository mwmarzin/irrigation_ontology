package irrigation.abox;

import java.util.*;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

public abstract class AbstractOntClass
{
    protected HashMap<String,Statement> statements = new HashMap<String,Statement>();
    protected Model model = null;
    protected Individual individual = null;
    
    public static Individual createIndividual(OntClass ontClass)
    {
        Individual individual = ontClass.createIndividual(ontClass.getURI() +  UUID.randomUUID());
        
        return individual;
    }
    
    public ArrayList<Statement> getStatements()
    {
        ArrayList <Statement> statementsList = new ArrayList<Statement>(statements.values());
        OntProperty property = null;
        OntProperty inverseProperty = null;
        for(Statement statement:statementsList)
        {
            if(statement.getPredicate() instanceof OntProperty)
            {
                property = (OntProperty)statement.getPredicate();
                inverseProperty = property.getInverse();
                if (inverseProperty != null)
                {
                    statementsList.add(model.createStatement(statement.getSubject(), inverseProperty, individual));
                }
            }
        }
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
    
    public void addStatement(String key, ObjectProperty objectProperty, Individual individual)
    {
        removeExistingStatement(key);
        statements.put(key,
                model.createLiteralStatement(individual, objectProperty, individual));
    }
    
    public void addLiteralStatement(String key, DatatypeProperty dataProperty, String value)
    {
        removeExistingStatement(key);
        statements.put(key,
                model.createLiteralStatement(individual, dataProperty, value));
    }
    
    public void addLiteralStatement(String key, DatatypeProperty dataProperty, double value)
    {
        removeExistingStatement(key);
        statements.put(key,
                model.createLiteralStatement(individual, dataProperty, value));
    }
}
