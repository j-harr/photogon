import tkinter as tk
from tkinter import messagebox

class PhotogonGUI:
    def __init__(self, master):
        master.title("Photogon Imaging System")
        master.iconbitmap('photogonlogo_YwQ_icon.ico')

        ##################
        # Controls Menu
        ##################
        self.controls_frame = tk.Frame(master, width=200)
        self.image_controls = tk.LabelFrame(self.controls_frame, text="Image Controls", width=200)
        self.network_controls = tk.LabelFrame(self.controls_frame, text="Network Controls", width=200)

        # Network Controls #######################################################
        self.find_devices_btn = tk.Button(self.network_controls, text="Find Devices", command=self.findDevices())
        self.configure_devices_btn = tk.Button(self.network_controls, text="Configure Devices")
        self.take_picture_btn = tk.Button(self.network_controls, text="Take Picture")

        self.find_devices_btn.pack(fill="both")
        self.configure_devices_btn.pack(fill="both")
        self.take_picture_btn.pack(fill="both")
        self.network_controls.pack(fill="both")

        # Image Controls #########################################################
        self.pipe1_btn = tk.Button(self.image_controls, text= "Pipeline1", command=self.pipeline1)
        self.pipe1_btn.pack(fill="both")

        self.pipe2_btn = tk.Button(self.image_controls, text="Pipeline2")
        self.pipe2_btn.pack(fill="both")

        self.image_controls.pack(fill="both")

        ################
        # Imaging Frame
        ################
        self.image_frame = tk.Frame(master, width="640", height="480", bg="black")

        # Place the control and image frames in the main window
        self.controls_frame.grid(row=0, column=0, sticky="nsew")
        self.image_frame.grid(row=0, column=1, sticky="nsew")

        master.grid_rowconfigure(0, weight=1)
        master.grid_columnconfigure(0, weight=1)

    def findDevices(self):
        tk.messagebox.showinfo("Coolio")

    def pipeline1(self):
        tk.messagebox.showinfo("Wow, this is neat")


root = tk.Tk()
p = PhotogonGUI(root)
root.mainloop()

"""
root = Tk()
root.title("Photogon Imaging System")
root.iconbitmap('photogonlogo_YwQ_icon.ico')
root.geometry('640x480+200+200')

root.grid_rowconfigure(0, weight=1)
root.grid_columnconfigure(1, weight=1)
# Create the two main frames
controls_frame = Frame(root, bg='white', width=200, height=480, pady=4).grid(row=0, column=0, sticky="ns")
image_frame = Frame(root, bg='black', width = 640, height=480, pady=4).grid(row=0, column=1, sticky="ns")

# Create the buttons
# Controls Frame
stream_btn = Button(controls_frame, text="Start Stream")
find_devices_btn = Button(controls_frame, text="Find Devices")
take_picture_btn = Button(controls_frame, text="Take Picture")
settings_btn = Button(controls_frame, text="Settings")
pipeline_btn = Button(controls_frame, text="Pipeline")
save_as_btn = Button(controls_frame, text="Save As")

find_devices_btn.grid(row=0, column=0)
take_picture_btn.grid(row=1, column=0)
pipeline_btn.grid(row=2, column=0)
save_as_btn.grid(row=3, column=0)

# Start the Main Loop #
root.mainloop()
"""