package irrigation.abox;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

import irrigation.generated.Irrigation;

import java.util.*;

public class Farmland extends AbstractOntClass
{

    private ArrayList<Patch> patches = null;
    public Farmland(Model model)
    {
        this.model = model;
        this.patches = new ArrayList<Patch>();
        this.individual = super.createIndividual(Irrigation.Farmland);
    }
    
    public void addPatch(Patch patch)
    {
        patches.add(patch);
    }
    
    @Override
    public ArrayList<Statement> getStatements()
    {
        ArrayList <Statement> statementsList = super.getStatements();
        
        for(Patch patch:patches)
        {
            statementsList.add(model.createStatement(individual, 
                    Irrigation.hasPatch, patch.getIndividual()));
            statementsList.addAll(patch.getStatements());
        }
        return statementsList;
    }
}
