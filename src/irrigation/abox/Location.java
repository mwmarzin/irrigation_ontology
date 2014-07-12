package irrigation.abox;

import irrigation.generated.Irrigation;

import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

public class Location extends AbstractOntClass
{

    public Location(Model model)
    {
        this.model = model;
        this.individual = Irrigation.Location.createIndividual(Irrigation.Location.getURI() + System.currentTimeMillis());
    }
}
