package irrigation.abox;

import com.hp.hpl.jena.rdf.model.Model;

import irrigation.generated.Irrigation;

public class Crop extends AbstractOntClass
{

    public Crop(Model model)
    {
        this.model = model;
        this.individual = Irrigation.Crop.createIndividual(Irrigation.Crop.getURI() + System.currentTimeMillis());
    }

}
