package net.greenmanov.iqdb.parsers;

/**
 * Type of image tag
 * COPYRIGHT - original source work for the imge
 * CIRCLE - circle of the image
 * CHARACTER - character appearing in the image
 * ARTIST - artist of the image
 * META - tags describing properties of the image
 * MEDIUM - medium where image originates
 * STUDIO - studio behind the image
 * STYLE - style of art on the image
 * SOURCE - source of the image
 * FAULTS - Faults of image file
 * GENERAL - tags that do not fall into other categories
 *
 * @author Lukáš Kurčík
 */
public enum TagType {
    COPYRIGHT, CIRCLE, CHARACTER, ARTIST, META, MEDIUM, STUDIO, STYLE, SOURCE, FAULTS, GENERAL
}
