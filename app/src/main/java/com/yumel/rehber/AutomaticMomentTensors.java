package com.yumel.rehber;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "automatic_moment_tensors")
public class AutomaticMomentTensors {

        @ElementList(inline = true, name = "earthquake")
        private List<Earthquake> earthquakeList;


    public List<Earthquake> getFocalMechanismsList() {
            return earthquakeList;
        }

        public void setFocalMechanismsList(List<Earthquake> earthquakeList) {
            this.earthquakeList = earthquakeList;
        }

/*    public List<FocalMechanism> focalMechanisms;

    public List<FocalMechanism> getFocalMechanismsList() {
        return focalMechanisms.stream()
                .filter(earthquake -> earthquake.getDate() != null)
                .collect(Collectors.toList());
    }*/

}
