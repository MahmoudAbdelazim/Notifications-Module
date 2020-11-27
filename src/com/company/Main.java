package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int option;
        TemplateManager templateManager = new FileTemplateManager("database.txt");

        while (true) {
            //main menu to test our module
            System.out.println("\n1- Show All Templates" +
                    "\n2- Add Template" +
                    "\n3- Remove Template" +
                    "\n4- Show Template" +
                    "\n5- Update Template" +
                    "\n6- Exit");
            Scanner in = new Scanner(System.in);
            option = in.nextInt();  // waiting the user for input
            in.skip("\n");

            if (option == 1) {
                ArrayList<NotificationTemplate> templates = templateManager.getAllTemplates();
                for (NotificationTemplate template : templates) {
                    System.out.println(template.toString());
                }

            } else if (option == 2) {
                System.out.println("Enter Subject:");
                String subject = in.nextLine();
                System.out.println("Language: " +
                        "\n1- English" +
                        "\nany other number - Arabic");
                int lang = in.nextInt();    // pick the desired language
                System.out.println("Channel: 1- Email" +
                        "\nany other number - SMS");
                int chan = in.nextInt();
                in.skip("\n");
                Language language;
                Channel channel;
                if (lang == 1) language = Language.English;
                else language = Language.Arabic;
                if (chan == 1) channel = Channel.Email;
                else channel = Channel.SMS;
                System.out.println("Enter Content: ");
                String content = in.nextLine();
                NotificationTemplate template = new NotificationTemplate(subject, language, channel);
                if (!template.editContent(content))
                    System.out.println("Placeholders are not matched, content will be empty");
                if (!templateManager.addTemplate(template))
                    System.out.println("There's already a template with the same subject");
                else
                    System.out.println("Template added successfully");

            } else if (option == 3) {
                System.out.println("Enter template Subject: ");
                String subject = in.nextLine();
                if (templateManager.deleteTemplate(subject))
                    System.out.println("Template deleted successfully");
                else
                    System.out.println("Template not found");

            } else if (option == 4) {
                System.out.println("Enter template Subject: ");
                String subject = in.nextLine();
                NotificationTemplate template = templateManager.getTemplate(subject);
                if (template != null)
                    System.out.println(templateManager.getTemplate(subject).toString());
                else
                    System.out.println("Template not found");

            } else if (option == 5) {
                System.out.println("Enter template current Subject: ");
                String subject = in.nextLine();
                NotificationTemplate template = templateManager.getTemplate(subject);
                if (template == null) {
                    System.out.println("Template not found");
                    continue;
                }
                System.out.println("Enter new Subject:");
                String newSubject = in.nextLine();
                System.out.println("Enter new Content: ");
                String content = in.nextLine();
                NotificationTemplate newTemplate = new NotificationTemplate(newSubject, template.getLanguage(), template.getChannel());
                if (!newTemplate.editContent(content)) {
                    System.out.println("Placeholders are not matched, Content is not changed");
                    newTemplate.editContent(template.getContent());
                }
                templateManager.updateTemplate(subject, newTemplate);
                System.out.println("Template updated successfully");

            } else if (option == 6) {
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }
}
