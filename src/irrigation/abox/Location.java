package irrigation.abox;

import irrigation.generated.Irrigation;

import java.util.List;
import java.util.UUID;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

public class Location extends AbstractOntClass
{

    public Location(Model model)
    {
        this.model = model;
        this.individual = super.createIndividual(Irrigation.Location);
    }
}
