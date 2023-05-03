package com.yumel.rehber;

public class FocalMechanismXmlParser {/*
    public List<FocalMechanism> parseFocalMechanisms(String xml) throws XmlPullParserException, IOException {
        List<FocalMechanism> focalMechanisms = new ArrayList<>();
        FocalMechanism focalMechanism = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(xml));

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            int eventType = parser.getEventType();
            String tagName = parser.getName();

            if (eventType == XmlPullParser.START_TAG) {
                if (tagName.equals("earthquake")) {
                    focalMechanism = new FocalMechanism();
                } else if (focalMechanism != null) {
                    if (tagName.equals("date")) {
                        focalMechanism.setDate(parser.nextText());
                    } else if (tagName.equals("time")) {
                        focalMechanism.setTime(parser.nextText());
                    } else if (tagName.equals("lon")) {
                        focalMechanism.setLon(Double.parseDouble(parser.nextText()));
                    } else if (tagName.equals("lat")) {
                        focalMechanism.setLat(Double.parseDouble(parser.nextText()));
                    } else if (tagName.equals("cent_depth")) {
                        focalMechanism.setCentDepth(Double.parseDouble(parser.nextText()));
                    } else if (tagName.equals("mw")) {
                        focalMechanism.setMw(Double.parseDouble(parser.nextText()));
                    } else if (tagName.equals("mo")) {
                        focalMechanism.setMo(Double.parseDouble(parser.nextText()));
                    } else if (tagName.equals("strike1")) {
                        focalMechanism.setStrike1(Integer.parseInt(parser.nextText()));
                    } else if (tagName.equals("dip1")) {
                        focalMechanism.setDip1(Integer.parseInt(parser.nextText()));
                    } else if (tagName.equals("rake1")) {
                        focalMechanism.setRake1(Integer.parseInt(parser.nextText()));
                    } else if (tagName.equals("strike2")) {
                        focalMechanism.setStrike2(Integer.parseInt(parser.nextText()));
                    } else if (tagName.equals("dip2")) {
                        focalMechanism.setDip2(Integer.parseInt(parser.nextText()));
                    } else if (tagName.equals("rake2")) {
                        focalMechanism.setRake2(Integer.parseInt(parser.nextText()));
                    } else if (tagName.equals("dc")) {
                        focalMechanism.setDc(Double.parseDouble(parser.nextText()));
                    } else if (tagName.equals("vr")) {
                        focalMechanism.setVr(Double.parseDouble(parser.nextText()));
                    } else if (tagName.equals("clvd")) {
                        focalMechanism.setClvd(parser.nextText());
                    } else if (tagName.equals("computed")) {
                        focalMechanism.setComputed(parser.nextText());
                    } else if (tagName.equals("method")) {
                        focalMechanism.setMethod(parser.nextText());
                    } else if (tagName.equals("beachball")) {
                        focalMechanism.setBeachball(parser.nextText());
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if (tagName.equals("earthquake")) {
                    focalMechanisms.add(focalMechanism);
                    focalMechanism = null;
                }
            }
        }
        return focalMechanisms;
    }*/
}
